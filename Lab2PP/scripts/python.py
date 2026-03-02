grupuri = {}

def adauga_la_grup(cuvant, suma):
    if suma not in grupuri:
        grupuri[suma] = []
    grupuri[suma].append(cuvant)

def afiseaza_duplicatele():
    print("\nCuvinte cu aceeasi suma de control:")
    for s, lista in grupuri.items():
        if len(lista) > 1:
            print(f"Suma {s}: {', '.join(lista)}")