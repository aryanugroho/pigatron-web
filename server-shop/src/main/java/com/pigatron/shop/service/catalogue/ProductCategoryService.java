package com.pigatron.shop.service.catalogue;

import com.google.common.collect.Lists;
import com.pigatron.shop.domain.entity.catalogue.ProductCategory;
import com.pigatron.shop.domain.repository.ProductCategoryRepository;
import com.pigatron.shop.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService extends AbstractRepositoryService<ProductCategory> {

	ProductCategoryRepository productCategoryRepository;

	@Autowired
	public ProductCategoryService(ProductCategoryRepository repository) {
		super(repository);
		this.productCategoryRepository = repository;
	}

	public ProductCategory addSubcategory(String parentId, ProductCategory newCategory) {
		ProductCategory parentCategory = repository.findOne(parentId);
		parentCategory.getSubcategories().add(newCategory);
		newCategory = repository.save(newCategory);
		repository.save(parentCategory);
		return newCategory;
	}

	@Override
	public void delete(String id) {
		List<ProductCategory> all = Lists.newArrayList(repository.findAll());
		ProductCategory deleteCategory = repository.findOne(id);
		all.stream()
				.filter(c -> c.getSubcategories().contains(deleteCategory))
				.forEach(c -> removeSubcategoryFromParent(c, deleteCategory));
		repository.delete(id);
	}

	private void removeSubcategoryFromParent(ProductCategory parent, ProductCategory child) {
		parent.getSubcategories().remove(child);
		repository.save(parent);
	}

}