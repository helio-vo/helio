class ProductController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ productInstanceList: Product.list( params ) ]
    }

    def show = {
        def productInstance = Product.get( params.id )

        if(!productInstance) {
            flash.message = "Product not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ productInstance : productInstance ] }
    }

    def delete = {
        def productInstance = Product.get( params.id )
        if(productInstance) {
            productInstance.delete()
            flash.message = "Product ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Product not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def productInstance = Product.get( params.id )

        if(!productInstance) {
            flash.message = "Product not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ productInstance : productInstance ]
        }
    }

    def update = {
        def productInstance = Product.get( params.id )
        if(productInstance) {
            productInstance.properties = params
            if(!productInstance.hasErrors() && productInstance.save()) {
                flash.message = "Product ${params.id} updated"
                redirect(action:show,id:productInstance.id)
            }
            else {
                render(view:'edit',model:[productInstance:productInstance])
            }
        }
        else {
            flash.message = "Product not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def productInstance = new Product()
        productInstance.properties = params
        return ['productInstance':productInstance]
    }

    def save = {
        def productInstance = new Product(params)
        if(!productInstance.hasErrors() && productInstance.save()) {
            flash.message = "Product ${productInstance.id} created"
            redirect(action:show,id:productInstance.id)
        }
        else {
            render(view:'create',model:[productInstance:productInstance])
        }
    }
}
