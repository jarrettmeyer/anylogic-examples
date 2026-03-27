double tryDispatch()
{/*ALCODESTART::1774650004001*/
if (orderQueue.isEmpty()) {
    return 0;
}
for (Truck truck : trucks) {
    if (truck.isIdling()) {
        StoreFront store = orderQueue.poll();
        if (store == null) {
            return 0;
        }
        pendingOrderCount = orderQueue.size();
        truck.send(store, this);
        return 0;
    }
}
return 0;
/*ALCODEEND*/}

