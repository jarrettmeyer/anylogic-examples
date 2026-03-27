void demandEvent()
{/*ALCODESTART::1774650000000*/
double demand = exponential(main.meanDemandSize);
localInventory = Math.max(0.0, localInventory - demand);
updateColor();
if (localInventory < main.reorderPoint && !hasPendingOrder) {
    hasPendingOrder = true;
    main.orderQueue.add(this);
    main.pendingOrderCount = main.orderQueue.size();
    main.tryDispatch();
}
/*ALCODEEND*/}

