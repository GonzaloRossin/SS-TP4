import matplotlib.pylab as plt
import pandas as pd

df = pd.read_json('dateDistance.json')

def distancia_vs_dia_de_despegue(dia, distancia, time):
    plt.plot(dia, distancia, 'b', label="distancia")

    # plt.twinx()
    # plt.plot(dia, time, 'r', label="tiempo")

    plt.xlabel("Dia de despegue (desde el 23/09/2022)", fontsize=16)
    plt.ylabel("Distancia a Venus", fontsize=16)
    plt.show()

distancia_vs_dia_de_despegue(df["date"], df["distance"], df["time"])

def velocidad_vs_tiempo(tiempo, velocidad):
    plt.plot(velocidad, tiempo, 'b')
    plt.xlabel("Tiempo pasado desde despegue (s)", fontsize=16)
    plt.ylabel("Modulo de la velocidad (km/s)", fontsize=16)
    plt.show()
