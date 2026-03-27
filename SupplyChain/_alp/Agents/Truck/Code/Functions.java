double tryLoad()
{/*ALCODESTART::1774650002000*/
double quantity = Math.min(main.orderQuantity, main.products);
main.products -= quantity;
loadedQuantity = quantity;
/*ALCODEEND*/}

boolean isIdling()
{/*ALCODESTART::1774650002001*/
return truckStatus.isStateActive(idling);
/*ALCODEEND*/}

