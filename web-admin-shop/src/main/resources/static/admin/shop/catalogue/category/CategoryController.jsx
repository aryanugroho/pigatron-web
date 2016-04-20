

class CategoryController extends AbstractFormController {

    /*@ngInject*/
    constructor($scope, $services, $rootScope, categoryService) {
        super($scope, $services, categoryService);
        this.$rootScope = $rootScope;
        window.$category = this;
        this.parentId = this.$stateParams.parentId;
    }

    save() {
        this.dataService.save(this.formData, this.parentId)
            .success((data) => {
                this.setPristine();
//                this.$rootScope.notifySuccess('Category Saved');
                this.$scope.$emit(EVENT_SHOP_CATALOGUE_CATEGORIES_CHANGED);
                this.$state.go("categories.category", {id: data.id});
            });
    }

    remove() {
        if(this.formData.id) {
            this.dataService.remove(this.formData.id).then(() => {
                this.afterRemove();
            });
        } else {
            this.afterRemove();
        }
    }

    afterRemove() {
        this.$state.go(ROUTE_SHOP_CATALOGUE_CATEGORIES);
        this.$scope.$emit(EVENT_SHOP_CATALOGUE_CATEGORIES_CHANGED);
    }

}

