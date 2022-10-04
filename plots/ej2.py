import matplotlib.pylab as plt
import pandas as pd


def distancia_vs_dia_de_despegue(dia, distancia):
    plt.scatter(dia, distancia, 'b')
    plt.xlabel("Dia de despegue (desde el 23/09/2022)", fontsize=16)
    plt.ylabel("Distancia a Venus", fontsize=16)
    plt.show()


def velocidad_vs_tiempo(tiempo, velocidad):
    plt.plot(velocidad, tiempo, 'b')
    plt.xlabel("Tiempo pasado desde despegue (s)", fontsize=16)
    plt.ylabel("Modulo de la velocidad (km/s)", fontsize=16)
    plt.show()
