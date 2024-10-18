package com.nuitriapp.equilibro.model;

public class CalculateurBMR {

    /**
     * Calculer le BMR en utilisant la formule de Harris-Benedict.
     *
     * @param poids kg
     * @param taille cm
     * @param age années
     * @param sexe "Homme" ou "Femme"
     * @return BMR en kcal/jour
     */
    public static double calculerBMR(double poids, double taille, int age, String sexe) {
        if (sexe.equalsIgnoreCase("Homme")) {
            return 88.362 + (13.397 * poids) + (4.799 * taille) - (5.677 * age);
        } else if (sexe.equalsIgnoreCase("Femme")) {
            return 447.593 + (9.247 * poids) + (3.098 * taille) - (4.330 * age);
        } else {
            throw new IllegalArgumentException("Sexe non valide");
        }
    }

    /**
     * Calculer les besoins caloriques en fonction du niveau d'activité.
     *
     * @param bmr
     * @param niveauActivite
     * @return Besoins caloriques
     */
    public static double calculerBesoinsCaloriques(double bmr, String niveauActivite) {
        switch (niveauActivite.toLowerCase()) {
            case "sédentaire":
                return bmr * 1.2;
            case "légèrement actif":
                return bmr * 1.375;
            case "modérément actif":
                return bmr * 1.55;
            case "très actif":
                return bmr * 1.725;
            case "extrêmement actif":
                return bmr * 1.9;
            default:
                throw new IllegalArgumentException("Niveau d'activité non valide");
        }
    }
}
