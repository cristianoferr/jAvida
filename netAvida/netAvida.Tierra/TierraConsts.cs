using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Tierra
{
    public class TierraConsts
    {
        public const string DEFAULT_ANCESTOR = "ancestor.txt";
        public const string GENEBANK_PATH = "genebank/";
        public const int REGISTRADORES = 4;

        public const int STACKS = 2;
        public const int MAX_STACK = 32;//qtd maxima por pilha

        public const int MEMORY_SIZE = 220000;

        public const int MAX_BUFFER = 16;

        public const int NORMAL_ERROR = 20;
        public const int CRITICAL_ERROR = 150;
        public const int NORMAL_FITNESS = 1;//rewarded to cells
        public const float MEMORY_USE = 80f;

        public const int GRAPH_WIDTH = 900;
        public const int GRAPH_SIZE = 2;
        public const int GRAPH_OFFSET = 10;

        public const float MUTATION_CHANCE = 0.03f;
        public const float MAX_MEMORY_CHILD = 1.5f;
        public const float MIN_MEMORY_CHILD = 0.5f;
        public const float DIVISION_MUTATION = 0.02f;
        public const float DIVISION_MIN_AMOUNT = 650;//quantidade minima de organismos para ficar ativo
        public const float RANDOM_MUTATION = 0.03f;//applied each CheckTick
        public const int ERROR_LIMIT = 500;


        public const float SIZE_REWARD = 0.1f; //recompensa dada ao organismo para cada byte
        public const float CHILD_REWARD = 10f;


        public const float ERROR_KILL_CHANCE = 0.02f;
        public const int MAX_ORGANISMOS = 500;

    }
}
