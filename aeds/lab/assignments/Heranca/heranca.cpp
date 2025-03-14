#include <iostream>

using namespace std;

class Forma
{
public:
    virtual double perimetro() = 0;

    virtual double area() = 0;

    virtual void imprimir() = 0;

    static void mostrarForma(Forma *forma)
    {
    }
};

class Retangulo : public Forma
{
private:
    double base;
    double altura;

public:
    double perimetro() override
    {
        return (base * 2) + (altura * 2);
    }

    double area() override
    {
        return base * altura;
    }

    
};

int main()
{
    return 0;
}