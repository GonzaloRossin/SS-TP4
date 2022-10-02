import matplotlib.pylab as plt
import pandas as pd


def mov_oscilador(timeAnalytical, posAnalytical, timeVerlet, posVerlet, timeBeeman, posBeeman, timeGCP, posGCP):
    plt.plot(timeAnalytical, posAnalytical, 'b-', label="Solución Analitica")
    plt.plot(timeVerlet, posVerlet, 'g--', label="Verlet")
    plt.plot(timeBeeman, posBeeman, 'r-.', label="Beeman")
    plt.plot(timeGCP, posGCP, 'k:', label="Gear predictor-corrector")
    plt.legend()
    plt.xlabel("Tiempo (s) ", fontsize=16)
    plt.ylabel("Posición del péndulo (m)", fontsize=16)
    plt.show()


def error(deltaVerlet, errorVerlet, deltaBeeman, errorBeeman, deltaGCP, errorGCP):
    plt.plot(deltaVerlet, errorVerlet, 'b-o', label="Verlet")
    plt.plot(deltaBeeman, errorBeeman, 'r-o', label="Beeman")
    plt.plot(deltaGCP, errorGCP, 'g-o', label="Gear predictor-corrector")
    plt.legend()
    plt.xlabel("Δt (s) ", fontsize=16)
    plt.ylabel("Error cuadrático medio (m²)", fontsize=16)
    plt.xscale("log")
    plt.yscale("log")


df = pd.read_json('positionOverTime.json')

pAnalytical = df['pAnalytical']
pBeeman = df['pBeeman']
pGCP = df['pGCP']
pVerlet = df['pVerlet']
x = df['time']

mov_oscilador(x, pAnalytical, x, pVerlet, x, pBeeman, x, pGCP)
