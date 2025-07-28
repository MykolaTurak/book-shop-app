package mate.academy.demo.service;

import static mate.academy.demo.util.TestUtil.getFirstCategory;
import static mate.academy.demo.util.TestUtil.getFirstCategoryDto;
import static mate.academy.demo.util.TestUtil.getFirstCategoryRequestDto;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import jdk.jfr.Description;
import mate.academy.demo.dto.category.CategoryDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.mapper.CategoryMapper;
import mate.academy.demo.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("""
            Save category with valid dto
            """)
    void save_WithValidData_ShouldReturnDtoWithId() {
        CategoryDto expected = getFirstCategoryDto();

        when(categoryRepository.save(getFirstCategory())).thenReturn(getFirstCategory());
        when(categoryMapper.toModel(getFirstCategoryRequestDto())).thenReturn(getFirstCategory());
        when(categoryMapper.toDto(getFirstCategory())).thenReturn(expected);

        CategoryDto actual = categoryService.save(getFirstCategoryRequestDto());

        assertEquals(expected, actual);

        verify(categoryRepository).save(getFirstCategory());
        verify(categoryMapper).toModel(getFirstCategoryRequestDto());
        verify(categoryMapper).toDto(getFirstCategory());
    }

    @Test
    @DisplayName("""
            Update category with valid data
            """)
    void update_WithValidData_ShouldReturnUpdatedDto() {
        when(categoryRepository.save(getFirstCategory())).thenReturn(getFirstCategory());
        Long categoryId = 1L;
        CategoryDto expected = new CategoryDto(categoryId,"Fantasy", "description");
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(getFirstCategory()));
        when(categoryMapper.toDto(getFirstCategory())).thenReturn(expected);

        CategoryDto actual = categoryService.update(getFirstCategoryRequestDto(), categoryId);

        verify(categoryRepository).findById(categoryId);
        verify(categoryMapper).toDto(getFirstCategory());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Update category with not valid id
            """)
    void update_WithNotValidId() {
        String expectedMessage = "Can't find category with id: " + getFirstCategory().getId();

        when(categoryRepository.findById(getFirstCategory().getId()))
                .thenReturn(Optional.empty());

        Exception actualException = Assert.assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(getFirstCategoryRequestDto(), getFirstCategory()
                        .getId()));

        verify(categoryRepository).findById(getFirstCategory().getId());

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    @Description("""
            Find by valid id
            """)
    void findById_WithValidId_ShouldReturnValidDto() {
        CategoryDto expected = new CategoryDto(
                getFirstCategory().getId(), "Fantasy", "description");

        when(categoryRepository.findById(getFirstCategory().getId()))
                .thenReturn(Optional.of(getFirstCategory()));
        when(categoryMapper.toDto(getFirstCategory())).thenReturn(expected);

        CategoryDto actual = categoryService.findById(getFirstCategory().getId());

        verify(categoryRepository).findById(getFirstCategory().getId());
        verify(categoryMapper).toDto(getFirstCategory());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find category by non exist id
            """)
    void findById_WithNonExistId_ShouldReturnException() {
        String expectMessage = "Can't find category with id: " + getFirstCategory().getId();

        when(categoryRepository.findById(getFirstCategory().getId()))
                .thenReturn(Optional.empty());

        Exception actualException = Assert.assertThrows(EntityNotFoundException.class,
                () -> categoryService.findById(getFirstCategory().getId()));

        verify(categoryRepository).findById(getFirstCategory().getId());

        assertEquals(expectMessage, actualException.getMessage());
    }
}
