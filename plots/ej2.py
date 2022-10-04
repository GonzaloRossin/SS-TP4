import matplotlib.pylab as plt
import pandas as pd

df = pd.read_json('dateDistance.json')
print(df["date"])

def distancia_vs_dia_de_despegue(dia, distancia):
    plt.scatter(dia, distancia)
    plt.xlabel("Dia de despegue (desde el 23/09/2022)", fontsize=16)
    plt.ylabel("Distancia a Venus", fontsize=16)
    plt.show()

distancia_vs_dia_de_despegue(df["date"], df["distance"])

def velocidad_vs_tiempo(tiempo, velocidad):
    plt.plot(velocidad, tiempo, 'b')
    plt.xlabel("Tiempo pasado desde despegue (s)", fontsize=16)
    plt.ylabel("Modulo de la velocidad (km/s)", fontsize=16)
    plt.show()
