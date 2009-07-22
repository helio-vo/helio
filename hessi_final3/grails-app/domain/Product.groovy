class Product {
    int id
    String productType
    String imageAlgorithm
    Date startDate
    int duration
    int minEnergy
    int maxEnergy
    String imageFileName
    String imageFilePath

    static belongsTo = [flare: Flare]

    static searchable = true

    static mapping = {
        version false
        id generator: 'assigned'
    }
    
    static constraints = {
        productType(nullable: true)
        imageAlgorithm(nullable: true)
        startDate(nullable: true)
        duration(nullable: true)
        minEnergy(nullable: true)
        maxEnergy(nullable: true)
        imageFileName(nullable: true)
        imageFilePath(nullable: true)
    }

    public String getProductTypeFull(){
        switch(productType){
            case "IM":
                return "Image"
            case "HPS":
                return "RHESSI Phoenix"
            case "HSG":
                return "RHESSI Spectogram"
            case "LC":
                return "Lightcurve"
            case "LC0":
                return "Lightcurve (LIN)"
            case "LC1":
                return "Lightcurve (LOG)"
            case "OS1":
                return "Trajectory"
            case "OS4":
                return "Obs. Summ. page"
            case "OS5":
                return "Time profiles page"
            case "OS7":
                return "Acpect Solution"
            case "OS8":
                return "Spectral Index"
            case "PTE":
                return "5x5 panel"
            case "PTN":
                return "NxM panel"
            case "SCA":
                return "3x3 panel"
            case "SP":
                return "Spectrum"
            case "ISP":
                return "ImSpec Panel"
            case "TC1":
                return "True Color"

        }
    }

	public String getImageAlgorithmFull(){
        switch(imageAlgorithm){
            case "BACK":
                return "Back Projection"
            case "CLEA":
                return "Clean"
            case "FOWD":
                return ""
            case "MEM":
                return "MEM Sato"
						default:
							return ""
        }
    }

    static transients = ['productTypeFull', 'imageAlgorithmFull']

}
