package mk.finki.ukim.mk.crimsonheart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import mk.finki.ukim.mk.crimsonheart.model.Institution;
import mk.finki.ukim.mk.crimsonheart.enums.InstitutionsType;
import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.service.InstitutionService;
import mk.finki.ukim.mk.crimsonheart.repository.InstitutionRepository;
import mk.finki.ukim.mk.crimsonheart.service.impl.InstitutionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import util.SubmissionHelper;

import java.util.Arrays;
import java.util.List;

public class FilterInstitutionTest {

    @Mock
    private InstitutionRepository institutionRepository;

    @InjectMocks
    private InstitutionServiceImpl institutionService;

    private Institution institution1;
    private Institution institution2;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);  // Ensure that Mockito initializes the mocks

        // Set up the test data
        institution1 = new Institution("Blood Bank 1", "123456789", "blood@bank.com", InstitutionsType.BLOOD_BANK, null);
        institution1.setId(1L);
        institution2 = new Institution("Red Cross 1", "987654321", "redcross@charity.com", InstitutionsType.RED_CROSS, null);
        institution2.setId(2L);
    }

    @Test
    public void test_filter_institutions() {
        // Start the test
        SubmissionHelper.startTest("test-filter-institutions");

        // Filter by name (text filter)
        when(institutionRepository.filterInstitutions("Blood Bank 1", InstitutionsType.BLOOD_BANK, "", CityEnum.SKOPJE)).thenReturn(Arrays.asList(institution1));
        List<Institution> institutions = institutionService.filterInstitution("Blood Bank 1", InstitutionsType.BLOOD_BANK, "", CityEnum.SKOPJE);
        assertEquals(1, institutions.size(), "Expected 1 institution with name containing 'Blood Bank 1'");
        assertEquals("Blood Bank 1", institutions.get(0).getName(), "Expected institution name to be 'Blood Bank 1'");

        // Reset the filter
        when(institutionRepository.filterInstitutions("Red Cross 1", InstitutionsType.RED_CROSS, "", CityEnum.TETOVO)).thenReturn(Arrays.asList(institution2));
        institutions = institutionService.filterInstitution("Red Cross 1", InstitutionsType.RED_CROSS, "", CityEnum.TETOVO);
        assertEquals(1, institutions.size(), "Expected 1 institution with name containing 'Red Cross 1'");
        assertEquals("Red Cross 1", institutions.get(0).getName(), "Expected institution name to be 'Red Cross 1'");

        // Filter by institution type (InstitutionsType.RED_CROSS)
        when(institutionRepository.filterInstitutions("", InstitutionsType.RED_CROSS, "", CityEnum.TETOVO)).thenReturn(Arrays.asList(institution2));
        institutions = institutionService.filterInstitution("", InstitutionsType.RED_CROSS, "", CityEnum.TETOVO);
        assertEquals(1, institutions.size(), "Expected 1 institution of type RED_CROSS");
        assertEquals("Red Cross 1", institutions.get(0).getName(), "Expected institution name to be 'Red Cross 1'");

        // Filter by city (CityEnum.SKOPJE)
        when(institutionRepository.filterInstitutions("", InstitutionsType.BLOOD_BANK, "", CityEnum.SKOPJE)).thenReturn(Arrays.asList(institution1));
        institutions = institutionService.filterInstitution("", InstitutionsType.BLOOD_BANK, "", CityEnum.SKOPJE);
        assertEquals(1, institutions.size(), "Expected 1 institution in SKOPJE");
        assertEquals("Blood Bank 1", institutions.get(0).getName(), "Expected institution name to be 'Blood Bank 1'");

        // Filter with no matches (non-existent institution)
        when(institutionRepository.filterInstitutions("Non-existent Institution", InstitutionsType.HOSPITAL, "", CityEnum.SKOPJE)).thenReturn(Arrays.asList());
        institutions = institutionService.filterInstitution("Non-existent Institution", InstitutionsType.HOSPITAL, "", CityEnum.SKOPJE);
        assertEquals(0, institutions.size(), "Expected no institutions for 'Non-existent Institution'");

        // End the test
        SubmissionHelper.endTest();
    }
}
