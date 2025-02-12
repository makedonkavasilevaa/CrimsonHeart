package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.model.Location;

import java.util.List;

public interface LocationCustomRepository {

    List<Location> filterLocations(String address, CityEnum city);
}
