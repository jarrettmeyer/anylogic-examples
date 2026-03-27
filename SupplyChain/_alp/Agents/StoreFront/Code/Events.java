void demandEvent()
{/*ALCODESTART::1774650000000*/
double demand = exponential(1.0 / main.meanDemandSize);
localInventory = Math.max(0.0, localInventory - demand);
updateColor();
if (localInventory < main.reorderPoint && !hasPendingOrder) {
    hasPendingOrder = true;
    main.orderQueue.add(this);
    main.pendingOrderCount = main.orderQueue.size();
    traceln("t=" + time() + " [store " + getIndex() + "] ordered, inventory=" + localInventory);
    main.tryDispatch();
}
/*ALCODEEND*/}

