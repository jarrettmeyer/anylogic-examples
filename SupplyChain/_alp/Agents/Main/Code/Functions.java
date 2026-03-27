double tryDispatch()
{/*ALCODESTART::1774650004001*/
if (orderQueue.isEmpty()) {
    return 0;
}
for (Truck truck : trucks) {
    if (truck.isIdling() && truck.assignedStore == null) {
        StoreFront store = orderQueue.poll();
        if (store == null) {
            return 0;
        }
        pendingOrderCount = orderQueue.size();
        traceln("t=" + time() + " dispatching truck to store, distributorInventory=" + distributorInventory);
        truck.assignedStore = store;
        return 0;
    }
}
traceln("t=" + time() + " tryDispatch: no idling truck found, queue size=" + orderQueue.size());
return 0;
/*ALCODEEND*/}

