package mk.finki.ukim.mk.crimsonheart.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

 import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "donation_event")
public class DonationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    @DateTimeFormat
    private Date dateAndTime;




}
