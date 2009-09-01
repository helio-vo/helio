class Flare {

    
   String goes;
   Integer positionX;
   Integer positionY;
   Integer positionZ;
   Integer radialOffset;
   Date startDate;
   String image;

     static constraints = {
        goes(blank:false)
        startDate(nullable:false)
        positionX(nullable:false)
        positionY(nullable:false)
        positionZ(nullable:false)
        radialOffset(nullable:false)
        image(nullable:false)
    }

}
