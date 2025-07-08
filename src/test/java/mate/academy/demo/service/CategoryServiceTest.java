package mate.academy.demo.service;

import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Optional;
import jdk.jfr.Description;
import mate.academy.demo.dto.category.CategoryDto;
import mate.academy.demo.dto.category.CreateCategoryRequestDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.mapper.CategoryMapper;
import mate.academy.demo.model.Category;
import mate.academy.demo.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        Category category = new Category();
        category.setName("Fantasy");
        category.setDescription("description");

        CreateCategoryRequestDto createCategoryRequestDto =
                new CreateCategoryRequestDto("Fantasy", "description");

        CategoryDto expected = new CategoryDto(1L,"Fantasy", "description"); // перенесено нижче

        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.toModel(createCategoryRequestDto)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.save(createCategoryRequestDto);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Update category with valid data
            """)
    void update_WithValidData_ShouldReturnUpdatedDto() {

        Category category = new Category();
        category.setName("Fantasy");
        category.setDescription("description");
        Mockito.when(categoryRepository.save(category)).thenReturn(category);
        Long categoryId = 1L;
        CategoryDto expected = new CategoryDto(categoryId,"Fantasy", "description");
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);

        CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto(
                "Fantasy", "description");

        CategoryDto actual = categoryService.update(createCategoryRequestDto, categoryId);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Update category with not valid id
            """)
    void update_WithNotValidId() {
        Long categoryId = 1L;
        CreateCategoryRequestDto createCategoryRequestDto = new CreateCategoryRequestDto(
                "Fantasy", "description");
        String expectedMessage = "Can't find category with id: " + categoryId;

        Mockito.when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception actualException = Assert.assertThrows(EntityNotFoundException.class,
                () -> categoryService.update(createCategoryRequestDto, categoryId));

        Assert.assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    @Description("""
            Find by valid id
            """)
    void findById_WithValidId_ShouldReturnValidDto() {
        Category category = new Category();
        Long categoryId = 1L;
        category.setId(categoryId);
        category.setName("Fantasy");
        category.setDescription("description");

        CategoryDto expected = new CategoryDto(categoryId, "Fantasy", "description");

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.findById(categoryId);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find category by non exist id
            """)
    void findById_WithNonExistId_ShouldReturnException() {
        Long categoryId = 1L;
        String expectMessage = "Can't find category with id: " + categoryId;

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Exception actualException = Assert.assertThrows(EntityNotFoundException.class,
                () -> categoryService.findById(categoryId));

        Assert.assertEquals(expectMessage, actualException.getMessage());
    }
}
