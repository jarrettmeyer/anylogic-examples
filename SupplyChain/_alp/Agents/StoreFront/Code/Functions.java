double updateColor()
{/*ALCODESTART::1774650001001*/
if (localInventory <= 0) {
    storeIcon.setFillColor(red);
} else if (localInventory < main.reorderPoint) {
    storeIcon.setFillColor(yellow);
} else {
    storeIcon.setFillColor(green);
}
/*ALCODEEND*/}

