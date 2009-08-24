class BootStrap {

     def init = { servletContext ->


        new Book(author:"Stephen King",title:"The Shining").save()
        new Book(author:"James Patterson",title:"Along Came a Spider").save()
        new Book(author:"James Patterson",title:"Along Came a Spider").save()

        
        new Flare(positionX:1,positionY:1,positionZ:1,radialOffset:1,goes:"James",startDate:new Date(),endDate:new Date()).save()
    new Flare(positionX:2,positionY:3,positionZ:1,radialOffset:1,goes:"James",startDate:new Date(),endDate:new Date()).save()
        
       
           
        
     }
     def destroy = {
     }
} 