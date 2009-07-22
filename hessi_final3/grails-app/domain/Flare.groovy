class Flare {

    String id
    int flareListId
    String eventType
    int distanceSun
    int posX
    int posY
    int sources
    String classSpecification
    double maxFlux
    String comments

    static hasMany = [products: Product]

    static searchable = {
        products component: true        
    }

    static mapping = {
        version false
        id generator: 'assigned'
    }

    static constraints = {
        flareListId(nullable:true)
        eventType(nullable:true)
        distanceSun(nullable:true)
        posX(nullable:true)
        posY(nullable:true)
        sources(nullable:true)
        classSpecification(nullable:true)
        maxFlux(nullable:true)
        comments(nullable:true)
    }

    public Date getMinStartDate(){
        Date temp = new Date()
        for(Product p : products){
            if(p.startDate.before(temp)) temp = p.startDate
        }
        return temp;
    }

    public Date getMaxEndDate(){
        Date temp = new GregorianCalendar(1900, 0, 1).getTime()
        Date endDate = new Date()

        for(Product p : products){
            endDate.setTime(p.startDate.getTime() + p.duration)
            if(endDate.after(temp)) temp.setTime(endDate.getTime())
        }
        return temp;
    }

    public String getThumbImage(){
        for(Product p : products){
            if(p.productType == "IM")
                return (p.imageFilePath + "/" + p.imageFileName)
        }
        return "noImg";
    }

    static transients = ['minStartDate', 'maxEndDate', 'thumbImage']



}
