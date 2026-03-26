# Phase 01

We are creating a model of a coffee shop in AnyLogic PLE 8.9.8. This is a demo project, designed to teach simulation fundamentals to new developers. We want to emphasize high-quality engineering in simulation design.

The time unit for this model is the **minute**.

## Shop Hours

The coffee shop is open for 8 hours a day, from 6:00 am to 2:00 pm. Customers who arrive when the shop is closed leave immediately without being served.

## Customer Arrivals

Customers arrive according to an exponential distribution with a mean interarrival time of 20 minutes.

## Order Fulfillment

There is a single worker. The worker fills orders according to a triangular distribution with a minimum of 1 minute, a mode of 3 minutes, and a maximum of 5 minutes (mean ≈ 3 minutes). Customers queue while the worker is busy.

## Seating

There are 12 seats in the shop. When a customer receives their order, they may choose to dine in or take away:

- **Dine in**: only possible if a seat is available. The customer lingers for a duration drawn from an exponential distribution with a mean of 30 minutes, then leaves.
- **Take away**: the customer leaves immediately after receiving their order.

In Phase 01, there are no ill-effects when no seats are available — customers simply take their order to go.

## Parameters

| Parameter                 | Type   | Default          | Description                                                      |
| ------------------------- | ------ | ---------------- | ---------------------------------------------------------------- |
| `baseTimeBetweenArrivals` | double | 20.0             | Mean minutes between customer arrivals                           |
| `minTimeToFillOrder`      | double | 1.0              | Minimum minutes to fill an order (Phase 01 experiment override)  |
| `modeTimeToFillOrder`     | double | 3.0              | Most likely minutes to fill an order (Phase 01 experiment override) |
| `maxTimeToFillOrder`      | double | 5.0              | Maximum minutes to fill an order (Phase 01 experiment override)  |
| `dailyOpenTime`           | double | 6.0 \* 60 (360)  | Minutes from midnight when shop opens                            |
| `dailyCloseTime`          | double | 14.0 \* 60 (840) | Minutes from midnight when shop closes                           |
| `baseDineInProbability`   | double | 0.50             | Probability a customer chooses to dine in (when seats available) |
| `baseTimeToLinger`        | double | 30.0             | Mean minutes a dine-in customer lingers                          |
| `seatCapacity`            | int    | 12               | Number of seats in the shop                                      |

## Phase 01 Experiment

Fixed seed = 1, stop at 525,600 min (1 year). All 9 parameters are exposed. Service time overrides: `minTimeToFillOrder=1.0, modeTimeToFillOrder=3.0, maxTimeToFillOrder=5.0` (mean ≈ 3 min).
