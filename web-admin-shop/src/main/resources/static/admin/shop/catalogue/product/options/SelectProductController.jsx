
class SelectProductController extends ProductsController {

    /*@ngInject*/
    constructor($scope, $services, $mdSidenav, productService) {
        super($scope, $services, productService);
        this.enableRowSelection(false);
        this.$mdSidenav = $mdSidenav;
    }

    load() {
        this.option = this.$scope.option;
        if(!this.option.products) {
            this.option.products = [];
        }
        this.option.waitingSelection = false;
        this.table.data = this.option.products;
    }

    loadConfig() {
        this.tableConfig = {
            visibleFields: ['unlink','sku','name','enabled','price','quantity']
        };
    }

    defineColumns() {
        let colDefs = super.defineColumns();
        colDefs.push(this.columnAction({ name:'unlink', icon:"fa-chain-broken", tooltip:"Remove Option", click:(row)=>{this.removeProduct(row);} }));
        return colDefs;
    }

    removeProduct(row) {
        let index = this.table.data.indexOf(row.entity);
        this.table.data.splice(index, 1);
        this.setDirty();
    }

    openRightSidebar() {
        this.$mdSidenav("sidenav-right").open();
        this.waitForSelection();
    }

    waitForSelection() {
        this.option.waitingSelection = true;
        this.offProductSelection = this.$scope.$on(EVENT_SHOP_CATALOGUE_PRODUCT_SELECTION,
            (event, args) => { this.onProductSelection(event, args); }
        );
        this.unwatchSidebar = this.$scope.$watch(
            () => { return this.$mdSidenav('sidenav-right').isOpen(); },
            (isOpen) => { if(!isOpen) this.onSidebarClose(); }
        );
    }

    onSidebarClose() {
        this.option.waitingSelection = false;
        this.offProductSelection();
        this.unwatchSidebar();
    }

    onProductSelection(event, selection) {
        console.log(selection);
        for(var i=0; i<selection.length; i++) {
            let product = selection[i];
            this.option.products.push(product);
            this.setDirty();
            //TODO can't add self / can't add duplicates
        }
    }

}

register('admin.shop.catalogue').controller('selectProductController', SelectProductController);
