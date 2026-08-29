package org.dara.walletservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dara.walletservice.model.constant.AssetType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "assets")
@Getter
@Setter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetType type;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "asset")
    private Set<WalletBalance> walletBalances = new HashSet<>();

}
