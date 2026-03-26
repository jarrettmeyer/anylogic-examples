double resetStartOfDay()
{/*ALCODESTART::1774531863650*/
dailyTotalWaitTime = 0.0;
dailyCustomersServed = 0;
dailyWorkerBusyTime = 0.0;
currentDay++;
/*ALCODEEND*/}

double leaveReview(double waitTime)
{/*ALCODESTART::1774532196321*/
boolean positiveExperience = waitTime < waitTimeSentimentThreshold;

if (positiveExperience) {
    if (uniform() < probabilityReviewGivenPositiveExperience) {
        positiveReviews++;
    }
} else {
    if (uniform() < probabilityReviewGivenNegativeExperience) {
        negativeReviews++;
    }
}
/*ALCODEEND*/}

double updateArrivalRate()
{/*ALCODESTART::1774532311499*/
if (reviewsAffectArrivalRate) {
    int totalReviews = positiveReviews + negativeReviews;
    if (totalReviews > 0) {
        double reviewRatio = (double) positiveReviews / (double) totalReviews;
        arrivalRateMultiplier = arrivalMultiplierMin + (arrivalMultiplierMax - arrivalMultiplierMin) * reviewRatio;
    }
}
/*ALCODEEND*/}

double getCustomerInterarrivalTime()
{/*ALCODESTART::1774534019690*/
double timeOfDay = time() % 1440;
double interarrivalTime = exponential(arrivalRateMultiplier / baseTimeBetweenArrivals);

if (timeOfDay < dailyOpenTime) {
    // Before opening: delay until the shop opens today
    interarrivalTime += dailyOpenTime - timeOfDay;
} else if (timeOfDay >= dailyCloseTime) {
    // After closing: delay until the shop opens tomorrow
    interarrivalTime += 1440.0 - timeOfDay + dailyOpenTime;
}

return interarrivalTime;
/*ALCODEEND*/}

