package com.novmah.blog.services.impl;

import com.novmah.blog.entities.Category;
import com.novmah.blog.exceptions.ResourceNotFoundException;
import com.novmah.blog.payloads.CategoryDto;
import com.novmah.blog.repositories.CategoryRepo;
import com.novmah.blog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;

    private final CategoryRepo categoryRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        categoryRepo.save(category);
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        categoryRepo.save(category);
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));

        categoryRepo.delete(category);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));

        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {

        List<Category> categories = categoryRepo.findAll();

        return categories.stream().map((category) -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}
