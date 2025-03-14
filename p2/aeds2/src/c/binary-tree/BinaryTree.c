#define node_type int

typedef struct Node {
    node_type value;
    struct Node *l, *r;
} Node;

Node root;

