package org.ateam.ateam.domain.link.entity;

import jakarta.persistence.*;
import lombok.*;
import org.ateam.ateam.domain.common.BaseEntity;
import org.ateam.ateam.domain.member.entity.Linker;
import org.ateam.ateam.domain.member.entity.Linko;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Link extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "linker_id")
    private Linker linker;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "linko_id")
    private Linko linko;

}
