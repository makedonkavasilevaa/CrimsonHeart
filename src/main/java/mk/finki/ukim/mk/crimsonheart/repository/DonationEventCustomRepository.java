package mk.finki.ukim.mk.crimsonheart.repository;

import mk.finki.ukim.mk.crimsonheart.enums.CityEnum;
import mk.finki.ukim.mk.crimsonheart.enums.DonationType;
import mk.finki.ukim.mk.crimsonheart.model.DonationEvent;

import java.util.List;

public interface DonationEventCustomRepository {

    List<DonationEvent> filterEvents (String name, DonationType donationType, CityEnum city);
}
