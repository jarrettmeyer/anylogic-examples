# Phase 02

We are building on Phase 01 of the coffee shop model. Phase 02 adds a customer feedback loop.

The coffee is always excellent, so wait time is the only factor that shapes customer sentiment. When a customer leaves, there is a probability that they leave a review — and the probability and sentiment of that review depends on how long they waited.

## Customer Experience

Wait time is measured as the total time from joining the order queue to receiving the order (queue wait + service time).

A single threshold parameter divides experiences into two categories:

- **Positive experience**: wait time is below `waitTimeSentimentThreshold`
- **Negative experience**: wait time is at or above `waitTimeSentimentThreshold`

## Reviews

Customers do not always leave reviews. The probability of leaving a review is conditioned on the experience type:

- `probabilityReviewGivenPositiveExperience` = 0.20
- `probabilityReviewGivenNegativeExperience` = 0.30

Reviews are tracked as two counters: `positiveReviews` and `negativeReviews`.

## Feedback Loop

The review ratio drives a multiplier on the customer arrival rate:

```
reviewRatio = positiveReviews / (positiveReviews + negativeReviews)
arrivalRateMultiplier = arrivalMultiplierMin + (arrivalMultiplierMax - arrivalMultiplierMin) * reviewRatio
```

When `reviewRatio = 0.5`, the multiplier is 1.0 (neutral). Better ratings attract more customers; worse ratings reduce them.

This feature can be toggled with the `reviewsAffectArrivalRate` parameter. When false, `arrivalRateMultiplier` stays at 1.0 and arrival behavior matches Phase 01.

## Parameters

| Parameter                                  | Type    | Default | Description                                                 |
| ------------------------------------------ | ------- | ------- | ----------------------------------------------------------- |
| `waitTimeSentimentThreshold`               | double  | 5.0     | Minutes; divides positive from negative experience          |
| `probabilityReviewGivenPositiveExperience` | double  | 0.20    | Probability of leaving a review after a positive experience |
| `probabilityReviewGivenNegativeExperience` | double  | 0.30    | Probability of leaving a review after a negative experience |
| `reviewsAffectArrivalRate`                 | boolean | true    | Enables the feedback loop                                   |
| `arrivalMultiplierMin`                     | double  | 0.5     | Arrival rate multiplier when all reviews are negative       |
| `arrivalMultiplierMax`                     | double  | 1.5     | Arrival rate multiplier when all reviews are positive       |

## Phase 02 Experiment

Fixed seed = 1, stop at 525,600 min (1 year). All 13 parameters are exposed.
