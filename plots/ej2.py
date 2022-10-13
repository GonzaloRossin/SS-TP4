from cProfile import label
import matplotlib.pylab as plt
import pandas as pd

df = pd.read_json('dateDistance.json')



def distancia_vs_min_de_despegue(minuto, distancia, time):
    plt.plot(minuto, distancia, 'b', label="Distancia")

    min_distance = min(distancia)
    min_index = distancia.index(min_distance)
    min_min = minuto[min_index]

    # plt.twinx()
    # plt.plot(dia, time, 'r', label="tiempo")

    plt.xlabel("Minuto de despegue", fontsize=16)
    plt.ylabel("Distancia a Venus", fontsize=16)
    plt.scatter(min_min, min_distance, c='k')
    plt.legend()
    plt.text(min_min, min_distance, min_min, size=10)
    plt.show()


def distancia_vs_dia_de_despegue(dia, distancia, time):
    plt.plot(dia, distancia, 'b', label="Distancia")

    min_distance = min(distancia)
    min_index = distancia.index(min_distance)
    min_day = dia[min_index]

    # plt.twinx()
    # plt.plot(dia, time, 'r', label="tiempo")

    plt.xlabel("Dia de despegue", fontsize=16)
    plt.ylabel("Distancia a Venus", fontsize=16)
    plt.scatter(min_day, min_distance, c='k')
    plt.legend()
    plt.text(min_day, min_distance, min_day, horizontalalignment='right', size=10)
    plt.show()


distancia_vs_dia_de_despegue(df["date"], df["distance"], df["time"])

df = pd.read_json('VmoduleTime.json')


def velocidad_vs_tiempo(tiempo, velocidad):
    plt.plot(tiempo, velocidad, 'b')
    plt.xlabel("Tiempo pasado desde despegue (días)", fontsize=16)
    plt.ylabel("Modulo de la velocidad (km/s)", fontsize=16)
    plt.show()


velocidad_vs_tiempo(df['time'], df['v'])


def v0VsTime():
    df = pd.read_json('minDistanceOverVelocity.json')
    plt.plot(df['velocity'], df['elapsed_time'], label='horas')
    plt.xlabel("Modulo de la velocidad (km/s)", fontsize=16)
    plt.ylabel("Tiempo pasado desde despegue", fontsize=16)
    plt.legend()
    plt.show()


v0VsTime()


def error(dt, error_energias):
    plt.scatter(dt, error_energias, 'b')

    # plt.twinx()
    # plt.plot(dia, time, 'r', label="tiempo")

    plt.xlabel("dt (s)", fontsize=16)
    plt.ylabel("Error", fontsize=16)
    plt.show()
