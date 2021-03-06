
const API_ADMIN_PRODUCT = "api/catalogue/product";

class ProductService extends webadmincore.AbstractRestService {

    /*@ngInject*/
    constructor($http) {
        super($http, API_ADMIN_PRODUCT);
    }

    getBySku(sku) {
        return this.$http.get(this.baseUrl + "?sku=" + sku);
    }

    getByUrlKey(urlKey) {
        return this.$http.get(this.baseUrl + "?urlKey=" + urlKey);
    }

}

module.exports = ProductService;