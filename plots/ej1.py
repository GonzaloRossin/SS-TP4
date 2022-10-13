import matplotlib.pylab as plt
import pandas as pd


def mov_oscilador(time, posAnalytical, posVerlet, posBeeman, posGCP):
    plt.plot(time, posAnalytical, 'b-', label="Solución Analitica")
    plt.plot(time, posVerlet, 'g--', label="Verlet")
    plt.plot(time, posBeeman, 'r-.', label="Beeman")
    plt.plot(time, posGCP, 'k:', label="Gear predictor-corrector")
    plt.legend()
    plt.xlabel("Tiempo (s) ", fontsize=16)
    plt.ylabel("Posición del péndulo (m)", fontsize=16)
    plt.show()


def errorOverTime(deltaT, errorVerlet, errorBeeman, errorGCP):
    plt.loglog(deltaT, errorVerlet, 'b-o', label="Verlet")
    plt.loglog(deltaT, errorBeeman, 'r-o', label="Beeman")
    plt.loglog(deltaT, errorGCP, 'g-o', label="Gear predictor-corrector")
    plt.legend()
    plt.xlabel("Δt (s) ", fontsize=16)
    plt.ylabel("Error cuadrático medio (m²)", fontsize=16)
    plt.show()

def errors(df):
    y = [df['errorVerlet'][0], df['errorBeeman'][0], df['errorGCP'][0]]
    x = ['Verlet', 'Beeman', 'GCP']
    plt.bar(x, y)
    plt.ylabel("Error cuadrático medio (m²)", fontsize=16)
    plt.show()

df = pd.read_json('positionOverTime.json')
df2 = pd.read_json('errorsOverDelta.json')

delta = df2['delta']
errorVerlet = df2['errorVerlet']
errorBeeman = df2['errorBeeman']
errorGCP = df2['errorGCP']

pAnalytical = df['pAnalytical']
pBeeman = df['pBeeman']
pGCP = df['pGCP']
pVerlet = df['pVerlet']
x = df['time']

#mov_oscilador(x, pAnalytical, pVerlet, pBeeman, pGCP)
#errorOverTime(delta, errorVerlet, errorBeeman, errorGCP)
df = pd.read_json('errorsPositions.json')
errors()
