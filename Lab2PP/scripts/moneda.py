def citeste_date():
    n = int(input("Numarul total de aruncari: "))
    x = int(input(f"Numarul maxim de pajuri (1-{n}): "))
    if not (1 <= x <= n):
        raise ValueError("x trebuie sa fie intre 1 si n")
    return n, x


from scipy.stats import binom

def probabilitate_binomiala(n, x):
    return binom.cdf(x, n, 0.5)  # probabilitatea de cel mult x pajuri


def main():
    n, x = citeste_date()
    p = probabilitate_binomiala(n, x)
    print(f"Probabilitatea de a obtine de cel mult {x} pajuri din {n} aruncari: {p:.4f}")

if __name__ == "__main__":
    main()