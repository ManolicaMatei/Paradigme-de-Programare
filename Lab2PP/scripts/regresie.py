import sys
import os
import matplotlib.pyplot as plt
import numpy as np

def main():
    # citim parametrii din linia de comanda
    # dataset.txt, numeImg, caleSalvare, culoare
    dataset_path = "scripts/dataset.txt"  # fix sau argument
    nume_fisier = sys.argv[1]
    cale_salvare = sys.argv[2]
    culoare_puncte = sys.argv[3]

    # citire dataset
    x_list = []
    y_list = []
    with open(dataset_path, "r") as f:
        for linie in f:
            parti = linie.strip().split(",")
            x_list.append(float(parti[0]))
            y_list.append(float(parti[1]))

    x = np.array(x_list)
    y = np.array(y_list)

    # Calcul coeficienti regresie
    panta, intercept = np.polyfit(x, y, 1)
    linie_regresie = panta * x + intercept

    # Grafic
    plt.figure(figsize=(8,5))
    plt.scatter(x, y, color=culoare_puncte, label='Date Intrare')
    plt.plot(x, linie_regresie, color='red', label='Linie Regresie')
    plt.title(f"Regresie: y = {panta:.2f}x + {intercept:.2f}")
    plt.legend()

    if not os.path.exists(cale_salvare):
        os.makedirs(cale_salvare)

    full_path = os.path.join(cale_salvare, nume_fisier + ".png")
    plt.savefig(full_path)
    plt.close()

    print(os.path.abspath(full_path))  # va fi citit de Java

if __name__ == "__main__":
    main()