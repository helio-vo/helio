class BootStrap {

     def init = { servletContext ->


        def tempDate = new Date();
        tempDate.setYear(100);
   def px=-832;
   def py= -91;
   def pz=836;


    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"C3.2",startDate:tempDate,image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"F3.2",startDate:tempDate,image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"A3.2",startDate:tempDate,image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"C3.2",startDate:tempDate,image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"E3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"A3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"F3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"A3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"F3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"C3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"A3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"F3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"C3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"E3.2",startDate:new Date(),image:"mythumb.jpg").save()
    new Flare(positionX:px,positionY:py,positionZ:pz,radialOffset:1,goes:"C3.2",startDate:new Date(),image:"mythumb.jpg").save()
        
       
           
        

     }
     def destroy = {
     }
} 