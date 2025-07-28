package mate.academy.demo.service;

import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.category.CategoryDto;
import mate.academy.demo.dto.category.CreateCategoryRequestDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.mapper.CategoryMapper;
import mate.academy.demo.model.Category;
import mate.academy.demo.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CreateCategoryRequestDto createCategoryRequestDto) {
        Category category = categoryMapper.toModel(createCategoryRequestDto);

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(CreateCategoryRequestDto createCategoryRequestDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category with id: " + id)
        );

        categoryMapper.updateModelFromDto(createCategoryRequestDto, category);

        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't find category with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category with id: " + id)));
    }
}
