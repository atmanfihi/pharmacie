package com.pharmacie.service;

import com.pharmacie.model.Categorie;
import com.pharmacie.model.DetailProduit;
import com.pharmacie.model.ERole;
import com.pharmacie.model.Fournisseur;
import com.pharmacie.model.Produit;
import com.pharmacie.model.Role;
import com.pharmacie.model.Tag;
import com.pharmacie.model.User;
import com.pharmacie.repository.CategorieRepository;
import com.pharmacie.repository.DetailProduitRepository;
import com.pharmacie.repository.FournisseurRepository;
import com.pharmacie.repository.ProduitRepository;
import com.pharmacie.repository.RoleRepository;
import com.pharmacie.repository.TagRepository;
import com.pharmacie.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.util.Set;

/**
 * Semencement initial des données (TP2) :
 *   - rôles ADMIN, PHARMACIEN, CAISSIER, CLIENT
 *   - utilisateurs par défaut (admin/admin123, pharma/pharma123, caisse/caisse123, client/client123)
 *   - jeu de données TP1 (catégories, fournisseurs, détails produits, tags, produits)
 *     inséré uniquement si la table produit est vide.
 */
@Configuration
public class InitData {

    @Bean
    CommandLineRunner init(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           CategorieRepository categorieRepository,
                           FournisseurRepository fournisseurRepository,
                           DetailProduitRepository detailProduitRepository,
                           TagRepository tagRepository,
                           ProduitRepository produitRepository) {
        return args -> {
            // ============ 1. Rôles (TP2) ============
            for (ERole role : ERole.values()) {
                if (roleRepository.findByName(role).isEmpty()) {
                    roleRepository.save(new Role(role));
                }
            }
            Role roleAdmin = roleRepository.findByName(ERole.ADMIN).orElseThrow();
            Role rolePharmacien = roleRepository.findByName(ERole.PHARMACIEN).orElseThrow();
            Role roleCaissier = roleRepository.findByName(ERole.CAISSIER).orElseThrow();
            Role roleClient = roleRepository.findByName(ERole.CLIENT).orElseThrow();

            // ============ 2. Utilisateurs par défaut (TP2) ============
            createUser(userRepository, passwordEncoder, "admin", "admin123", "admin@pharmacie.ma",
                    Set.of(roleAdmin));
            createUser(userRepository, passwordEncoder, "pharma", "pharma123", "pharma@pharmacie.ma",
                    Set.of(rolePharmacien));
            createUser(userRepository, passwordEncoder, "caisse", "caisse123", "caisse@pharmacie.ma",
                    Set.of(roleCaissier));
            createUser(userRepository, passwordEncoder, "client", "client123", "client@pharmacie.ma",
                    Set.of(roleClient));

            // ============ 3. Jeu de données TP1 (uniquement si vide) ============
            if (produitRepository.count() > 0) {
                return;
            }

            // --- Catégories ---
            Categorie catAntibiotiques = categorieRepository.save(new Categorie(
                    "Antibiotiques", "Médicaments utilisés pour traiter les infections bactériennes."));
            Categorie catAntalgiques = categorieRepository.save(new Categorie(
                    "Antalgiques", "Médicaments pour soulager la douleur."));
            Categorie catVitamines = categorieRepository.save(new Categorie(
                    "Vitamines", "Compléments nutritionnels et fortifiants."));
            Categorie catAntiseptiques = categorieRepository.save(new Categorie(
                    "Antiseptiques", "Produits désinfectants pour usage externe."));

            // --- Fournisseurs ---
            Fournisseur fourPharmaMaroc = fournisseurRepository.save(new Fournisseur(
                    "PharmaMaroc", "contact@pharmamaroc.ma", "Casablanca, Maroc"));
            Fournisseur fourMedisup = fournisseurRepository.save(new Fournisseur(
                    "Medisup France", "info@medisup.fr", "Paris, France"));
            Fournisseur fourSandoz = fournisseurRepository.save(new Fournisseur(
                    "Sandoz", "export@sandoz.com", "Bâle, Suisse"));
            Fournisseur fourCooper = fournisseurRepository.save(new Fournisseur(
                    "Cooper Pharma", "support@cooperpharma.ma", "Rabat, Maroc"));

            // --- Détails produits ---
            DetailProduit det1 = detailProduitRepository.save(new DetailProduit(
                    "Sanofi", "France", "Amoxicilline trihydratée 500 mg", "500 mg",
                    "Infections ORL et respiratoires"));
            DetailProduit det2 = detailProduitRepository.save(new DetailProduit(
                    "Bayer", "Allemagne", "Paracétamol 500 mg", "500 mg",
                    "Douleurs légères à modérées, fièvre"));
            DetailProduit det3 = detailProduitRepository.save(new DetailProduit(
                    "Pfizer", "États-Unis", "Vitamine C 1000 mg", "1000 mg",
                    "Renforcement immunitaire"));
            DetailProduit det4 = detailProduitRepository.save(new DetailProduit(
                    "Johnson & Johnson", "Maroc", "Povidone iodée 10%", "10%",
                    "Désinfection des plaies"));
            DetailProduit det5 = detailProduitRepository.save(new DetailProduit(
                    "GSK", "Royaume-Uni", "Ibuprofène 400 mg", "400 mg",
                    "Inflammations et douleurs musculaires"));

            // --- Tags ---
            Tag tagSansOrdonnance = tagRepository.save(new Tag("Sans ordonnance"));
            Tag tagPrescription = tagRepository.save(new Tag("Prescription obligatoire"));
            Tag tagPromotion = tagRepository.save(new Tag("En promotion"));
            Tag tagStockLimite = tagRepository.save(new Tag("Stock limité"));
            Tag tagImporte = tagRepository.save(new Tag("Importé"));

            // --- Produits ---
            Produit p1 = new Produit();
            p1.setNom("Amoxicilline 500mg");
            p1.setDescription("Antibiotique à large spectre.");
            p1.setPrix(45.00);
            p1.setCodeBarre("AMX500-2025");
            p1.setDateExpiration(LocalDate.of(2026, 3, 10));
            p1.setCategorie(catAntibiotiques);
            p1.setFournisseur(fourPharmaMaroc);
            p1.setDetailProduit(det1);
            p1.getTags().add(tagPrescription);
            produitRepository.save(p1);

            Produit p2 = new Produit();
            p2.setNom("Doliprane 500mg");
            p2.setDescription("Antalgique et antipyrétique.");
            p2.setPrix(25.50);
            p2.setCodeBarre("DLP500-2025");
            p2.setDateExpiration(LocalDate.of(2025, 11, 1));
            p2.setCategorie(catAntalgiques);
            p2.setFournisseur(fourMedisup);
            p2.setDetailProduit(det2);
            p2.getTags().add(tagSansOrdonnance);
            p2.getTags().add(tagPromotion);
            produitRepository.save(p2);

            Produit p3 = new Produit();
            p3.setNom("Vitamine C 1000mg");
            p3.setDescription("Complément pour renforcer les défenses.");
            p3.setPrix(30.00);
            p3.setCodeBarre("VITC1000-2025");
            p3.setDateExpiration(LocalDate.of(2026, 6, 15));
            p3.setCategorie(catVitamines);
            p3.setFournisseur(fourSandoz);
            p3.setDetailProduit(det3);
            p3.getTags().add(tagSansOrdonnance);
            p3.getTags().add(tagImporte);
            produitRepository.save(p3);

            Produit p4 = new Produit();
            p4.setNom("Bétadine Solution 10%");
            p4.setDescription("Désinfectant pour usage externe.");
            p4.setPrix(18.00);
            p4.setCodeBarre("BETADINE10-2025");
            p4.setDateExpiration(LocalDate.of(2027, 1, 30));
            p4.setCategorie(catAntiseptiques);
            p4.setFournisseur(fourCooper);
            p4.setDetailProduit(det4);
            p4.getTags().add(tagSansOrdonnance);
            p4.getTags().add(tagStockLimite);
            produitRepository.save(p4);

            Produit p5 = new Produit();
            p5.setNom("Nurofen 400mg");
            p5.setDescription("Soulage la douleur et la fièvre.");
            p5.setPrix(42.00);
            p5.setCodeBarre("NURO400-2025");
            p5.setDateExpiration(LocalDate.of(2026, 2, 20));
            p5.setCategorie(catAntalgiques);
            p5.setFournisseur(fourSandoz);
            p5.setDetailProduit(det5);
            p5.getTags().add(tagSansOrdonnance);
            p5.getTags().add(tagImporte);
            produitRepository.save(p5);

            System.out.println(">>>> Initialisation des données terminée (rôles, utilisateurs, jeu de données TP1).");
        };
    }

    private void createUser(UserRepository userRepository, PasswordEncoder passwordEncoder,
                            String username, String password, String email, Set<Role> roles) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User(username, passwordEncoder.encode(password), email);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
