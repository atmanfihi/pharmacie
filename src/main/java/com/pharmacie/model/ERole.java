package com.pharmacie.model;

/**
 * Rôles utilisateurs de l'application (TP2 - Sécurisation).
 * Les autorités Spring Security sont construites comme "ROLE_" + name().
 */
public enum ERole {
    ADMIN,
    PHARMACIEN,
    CAISSIER,
    CLIENT
}
