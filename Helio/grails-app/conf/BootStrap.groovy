class BootStrap {

     def init = { servletContext ->


        def tempDate = new Date();
        tempDate.setYear(100);
   

    new Flare(positionX:1,positionY:1,positionZ:1,radialOffset:1,goes:"James",startDate:tempDate,image:"mythumb.jpg").save()
    new Flare(positionX:2,positionY:3,positionZ:1,radialOffset:1,goes:"James1",startDate:tempDate,image:"mythumb.jpg").save()
    new Flare(positionX:2,positionY:3,positionZ:1,radialOffset:1,goes:"James2",startDate:tempDate,image:"mythumb.jpg").save()
    new Flare(positionX:2,positionY:3,positionZ:1,radialOffset:1,goes:"James3",startDate:tempDate,image:"mythumb.jpg").save()
    new Flare(positionX:2,positionY:3,positionZ:1,radialOffset:1,goes:"James4",startDate:new Date(),image:"mythumb.jpg").save()
        
       
           
        

     }
     def destroy = {
     }
} 