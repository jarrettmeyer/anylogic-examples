double tryLoad()
{/*ALCODESTART::1774650002000*/
int quantity = (int) Math.round(Math.min(main.maxInventory - assignedStore.localInventory, main.distributorInventory));
main.distributorInventory -= quantity;
loadedQuantity = quantity;
/*ALCODEEND*/}

boolean isIdling()
{/*ALCODESTART::1774650002001*/
return truckStatus.isStateActive(idling);
/*ALCODEEND*/}

