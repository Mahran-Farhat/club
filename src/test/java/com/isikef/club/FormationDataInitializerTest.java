package com.isikef.club;

import com.isikef.club.entities.Club;
import com.isikef.club.entities.Formation;
import com.isikef.club.entities.ThemeFormation;
import com.isikef.club.repository.ClubRepository;
import com.isikef.club.repository.FormationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FormationDataInitializerTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Test
    void shouldInitializeTwentyThreeFormationsForClubOne() {
        Club club = clubRepository.findById(1L).orElseGet(() -> {
            Club newClub = new Club();
            newClub.setName("Club 1");
            newClub.setDescription("Club used for test data initialization");
            Date now = new Date();
            newClub.setCreateDate(now);
            newClub.setUpdateDate(now);
            return clubRepository.save(newClub);
        });

        formationRepository.deleteByClubId(club.getId());

        List<ThemeFormation> expectedThemes = List.of(
                ThemeFormation.JAVA,
                ThemeFormation.PYTHON,
                ThemeFormation.JAVASCRIPT,
                ThemeFormation.CSHARP,
                ThemeFormation.RUBY,
                ThemeFormation.PHP,
                ThemeFormation.SWIFT,
                ThemeFormation.KOTLIN,
                ThemeFormation.GO,
                ThemeFormation.RUST,
                ThemeFormation.HTML_CSS,
                ThemeFormation.SQL,
                ThemeFormation.TYPESCRIPT,
                ThemeFormation.CPLUSPLUS,
                ThemeFormation.C,
                ThemeFormation.JAVA,
                ThemeFormation.PYTHON,
                ThemeFormation.JAVASCRIPT,
                ThemeFormation.CSHARP,
                ThemeFormation.RUBY,
                ThemeFormation.PHP,
                ThemeFormation.SWIFT,
                ThemeFormation.KOTLIN
        );

        List<Formation> formations = IntStream.range(0, expectedThemes.size())
                .mapToObj(index -> {
                    Formation formation = new Formation();
                    formation.setTheme(expectedThemes.get(index));
                    formation.setClub(club);
                    return formation;
                })
                .toList();

        formationRepository.saveAllAndFlush(formations);

        List<Formation> clubFormations = formationRepository.findAll().stream()
                .filter(formation -> formation.getClub() != null && club.getId().equals(formation.getClub().getId()))
                .toList();

        assertThat(clubFormations).hasSize(23);
        assertThat(clubFormations.stream().map(Formation::getTheme).toList())
                .containsExactlyElementsOf(expectedThemes);
    }
}
