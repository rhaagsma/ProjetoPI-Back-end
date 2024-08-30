package com.example.siteDiscoBackend.Showcase;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShowcaseRepository extends JpaRepository<Showcase, UUID> {
}
