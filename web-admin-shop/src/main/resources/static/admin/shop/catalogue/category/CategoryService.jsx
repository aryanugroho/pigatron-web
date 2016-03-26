
const API_ADMIN_CATEGORY = "/shopadmin/api/catalogue/category";
const ROOT_ID = "root";


class CategoryService extends AbstractRestService {

    constructor($http) {
        super($http, API_ADMIN_CATEGORY);
    }

    getRoot() {
        return this.get(ROOT_ID);
    }

    save(category, parentId) {
        return this.$http.post(category.id == null ? this.getUrl(parentId) : this.getUrl(), category);
    }

}