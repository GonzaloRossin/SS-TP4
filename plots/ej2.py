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

df = pd.read_json('VmoduleTime.json')

def velocidad_vs_tiempo(tiempo, velocidad):
    plt.plot(tiempo, velocidad, 'b')
    plt.xlabel("Tiempo pasado desde despegue (días)", fontsize=16)
    plt.ylabel("Modulo de la velocidad (km/s)", fontsize=16)
    plt.show()

velocidad_vs_tiempo(df['time'],df['v'])

def v0VsTime():
    df = pd.read_json('minDistanceOverVelocity.json')
    plt.plot(df['velocity'], df['elapsed_time'])
    plt.xlabel("Modulo de la velocidad (km/s)", fontsize=16)
    plt.ylabel("Tiempo pasado desde despegue (días)", fontsize=16)
    plt.show()

v0VsTime()