# Phase 03

We are building on Phase 02 of the coffee shop model. Phase 03 adds dynamic worker management: the shop hires and fires workers based on daily performance metrics.

The time unit for this model is the **minute**.

## Business Hours

The source generates customer arrivals continuously, but each potential arrival checks whether the shop is open (`dailyOpenTime ≤ time() % 1440 < dailyCloseTime`) via the `onBeforeArrival` hook. Arrivals outside business hours are cancelled before the agent is created, keeping the total agent count well under AnyLogic PLE's 50,000 limit over a year-long run. Customers already in the queue at close time are served out normally — no forced clearing.

## Service Time Distribution

Service time is drawn from a triangular distribution: `triangular(minTimeToFillOrder, modeTimeToFillOrder, maxTimeToFillOrder)`, divided by the worker efficiency factor. Triangular guarantees a minimum and maximum service time and is parameterized as min/mode/max.

## Worker Hiring and Firing

At midnight each day (`endOfDay` cyclic event, recurrence 1440 min), the model evaluates two metrics from the previous day:

- **Average wait time**: total wait time across all served customers divided by customers served (queue wait + service time)
- **Worker utilization**: total busy time across all workers divided by available worker-hours (operating hours × worker count)

These metrics drive one decision per day:

- **Hire**: if average wait time is at or above `hireWaitTimeThreshold`, add one worker — up to the maximum set by the `workerEfficiencyTable` length
- **Fire**: if worker utilization is below `fireUtilizationThreshold`, remove one worker — down to a minimum of 1
- Hire takes priority over fire; only one change per day

The feature can be toggled with the `hireWorkers` parameter. When false, worker count stays at 1 and arrival and service behavior matches Phase 02.

## Worker Efficiency

Adding workers provides extra capacity but degrades efficiency due to competition for shared equipment (espresso machines, milk steamers, etc.). Efficiency is modeled as a lookup table indexed by worker count:

| Workers | Efficiency |
| ------- | ---------- |
| 1       | 100%       |
| 2       | 100%       |
| 3       | 90%        |
| 4       | 70%        |
| 5       | 50%        |

The efficiency multiplier scales the triangular service time distribution for order fulfillment. Lower efficiency means longer service time — the drawn duration is divided by the efficiency factor. The table length also determines the maximum number of workers (5 workers by default).

## Parameters

| Parameter                  | Type     | Default                   | Description                                                           |
| -------------------------- | -------- | ------------------------- | --------------------------------------------------------------------- |
| `hireWorkers`              | boolean  | true                      | Enables daily worker hiring and firing                                |
| `hireWaitTimeThreshold`    | double   | 8.0                       | Mean minutes of daily average wait time that triggers hiring a worker |
| `fireUtilizationThreshold` | double   | 0.30                      | Daily worker utilization below which a worker is fired                |
| `workerEfficiencyTable`    | double[] | {1.0, 1.0, 0.9, 0.7, 0.5} | Efficiency multiplier per worker count; array length sets the maximum |

All 16 parameters from Phases 01 and 02 are also present (including `minTimeToFillOrder`, `modeTimeToFillOrder`, and `maxTimeToFillOrder`).

## Phase 03 Experiment

Fixed seed = 1, stop at 525,600 min (1 year). All 20 parameters are exposed.
