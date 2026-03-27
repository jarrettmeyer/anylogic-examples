# MachineDegradation

An agent-based simulation of a fleet of machines with coupled operating and condition statecharts, built with AnyLogic PLE 8.9.8. The time unit is the **hour**.

## Model Overview

Each machine runs two parallel statecharts simultaneously: one tracking its **operation** (idle or running) and one tracking its **condition** (nominal or degraded). The statecharts are coupled — the rate of each transition depends on the current state of the other statechart.

When a machine's condition changes, it sends a message to itself. The operation statechart receives the message via a self-loop, which forces AnyLogic to redraw the pending timeout at the new rate. The same happens in reverse when operation changes. This coupling means a machine that degrades while running will immediately begin breaking down more frequently.

Main tracks the fleet by counting machines in each of the four combined states every hour. A stacked time chart displays the fleet distribution over time.

## Statecharts

### Operation Statechart (`machineOperation`)

States: `idle` (initial), `running`

| Transition | Rate |
| :--- | :--- |
| `idle` → `running` | `1 / getStartupTime()` per hour |
| `running` → `idle` | `1 / getRunningTime()` per hour |

`getStartupTime()` returns `meanStartupTimeWhenNominal` or `meanStartupTimeWhenDegraded` based on the condition statechart state. `getRunningTime()` works the same way.

### Condition Statechart (`machineCondition`)

States: `nominal` (initial), `degraded`

| Transition | Rate |
| :--- | :--- |
| `nominal` → `degraded` | `1 / getDegradationTime()` per hour |
| `degraded` → `nominal` | `1 / getRestorationTime()` per hour |

`getDegradationTime()` returns `meanDegradationTimeWhenIdle` or `meanDegradationTimeWhenRunning` based on the operation statechart state. `getRestorationTime()` works the same way.

## Parameters

| Name | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `machineCount` | int | 20 | Number of machines in the fleet |
| `meanStartupTimeWhenNominal` | double | 0.5 | Mean hours for a nominal machine to start running |
| `meanStartupTimeWhenDegraded` | double | 2.0 | Mean hours for a degraded machine to start running |
| `meanRunningTimeWhenNominal` | double | 8.0 | Mean hours a nominal machine runs before going idle |
| `meanRunningTimeWhenDegraded` | double | 1.0 | Mean hours a degraded machine runs before going idle |
| `meanDegradationTimeWhenIdle` | double | 168.0 | Mean hours until an idle machine degrades (≈ 1 week) |
| `meanDegradationTimeWhenRunning` | double | 48.0 | Mean hours until a running machine degrades (≈ 2 days) |
| `meanRestorationTimeWhenIdle` | double | 1.0 | Mean hours to restore an idle degraded machine |
| `meanRestorationTimeWhenRunning` | double | 72.0 | Mean hours to restore a running degraded machine (≈ 3 days) |

## Experiments

### Phase01 — Operation only (baseline)

Condition statechart is effectively locked to `nominal` by setting both degradation times to `1.0E9` hours. Machines cycle between `idle` and `running` at the nominal rates only.

Expected behavior: only Running+Nominal and Idle+Nominal states appear. Running count mean ≈ `machineCount × R / (R + S)` where R = `meanRunningTimeWhenNominal` and S = `meanStartupTimeWhenNominal`.

### Phase02 — Full coupling (default parameters)

Both statecharts active with all default parameters. Degradation times are long (48–168 hrs) and restoration when idle is fast (1 hr), so the fleet stays mostly nominal. A small number of degraded machines are visible at any time, cycling quickly through `idle` once they stop.

### Calibrated — Tuned for visible dynamics

Parameters tuned so all four combined states are visibly present at equilibrium.

| Parameter | Value |
| :--- | :--- |
| `meanStartupTimeWhenNominal` | 2.0 |
| `meanStartupTimeWhenDegraded` | 4.0 |
| `meanRunningTimeWhenNominal` | 5.0 |
| `meanRunningTimeWhenDegraded` | 2.0 |
| `meanDegradationTimeWhenIdle` | 24.0 |
| `meanDegradationTimeWhenRunning` | 6.0 |
| `meanRestorationTimeWhenIdle` | 0.5 |
| `meanRestorationTimeWhenRunning` | 16.0 |

Expected steady state (20 machines): ~10 Running+Nominal, ~3 Running+Degraded, ~6 Idle+Nominal, ~1 Idle+Degraded.

## Design Decisions

**Exponential distribution for all transition times.** Both statecharts use rate-triggered transitions, which are equivalent to exponential holding times. This is the correct choice because each machine is always in exactly one state in each statechart, and the memoryless property of the exponential distribution means the pending time-to-transition does not depend on how long the machine has already been in that state.

This matters for the coupling mechanism: when a machine degrades while running, the `running → idle` self-loop fires, discarding the old pending timeout and drawing a new one at the degraded rate. With a non-memoryless distribution (e.g., triangular, normal), discarding a partially-elapsed timeout and restarting would introduce bias — the machine would effectively get a "fresh draw" at an arbitrary point in its holding time. The exponential distribution avoids this entirely: a fresh draw and a residual draw are statistically identical.

**Message-triggered self-loops for rate recalculation.** When the condition statechart transitions, it sends `"Condition Changed"` to itself. The operation statechart has self-loops on both `idle` and `running` that filter for this message. Firing the self-loop causes AnyLogic to re-enter the state and redraw the pending timeout using the current rate expression, which now queries the updated condition state. The same pattern runs in reverse (`"Operation Changed"`) for the condition statechart. This avoids polling and keeps the coupling event-driven.

**Recount-from-scratch for fleet counters.** Main uses a cyclic event (every 1 hour) to recount all machines by calling `m.isIdleNominal()`, `m.isRunningDegraded()`, etc., rather than incrementing and decrementing counters in transition actions. Incremental tracking is fragile during startup because AnyLogic initializes agents sequentially — a machine's operation statechart may fire its entry action before its condition statechart is active, causing the condition query to return false for both states and producing incorrect initial counts. Recount-from-scratch sidesteps this entirely.
