package mate.academy.demo.service;

import mate.academy.demo.dto.category.CategoryDto;
import mate.academy.demo.dto.category.CreateCategoryRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto save(CreateCategoryRequestDto createCategoryRequestDto);

    CategoryDto update(CreateCategoryRequestDto createCategoryRequestDto, Long id);

    void delete(Long id);

    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto findById(Long id);
}
