/*
	Guia_0105.v
	842536 - Mateus Henrique Medeiros Diniz
*/
module Guia_0105;
	reg [0 : 13][7 : 0] s;
	
	initial
	begin: main
		integer i;
		
		s = "PUC-M.G.";
		
		$write("a) ");
		for(i = 6; i < 14; i++) begin
			$write("%x ", s[i]);
		end
		$write("(16)\n");
		
		s = "2024-02";
		
		$write("b) ");
		for(i = 7; i < 14; i++) begin
			$write("%x ", s[i]);
		end
		$write("(16)\n");
		
		s = "Belo Horizonte";
		
		$write("c) ");
		for(i = 0; i < 14; i++) begin
			$write("%b ", s[i]);
		end
		$write("(2)\n");	
		
		s[0] = 8'o156;
		s[1] = 8'o157;
		s[2] = 8'o151;
		s[3] = 8'o164;
		s[4] = 8'o145;
		
		$write("d) \"");
		for(i = 0; i < 5; i++) begin
			$write("%c", s[i]);
		end
		$write("\"\n");
		
		s[0] = 8'h4d;
		s[1] = 8'h61;
		s[2] = 8'h6e;
		s[3] = 8'h68;
		s[4] = 8'h61;
		
		$write("e) \"");
		for(i = 0; i < 5; i++) begin
			$write("%c", s[i]);
		end
		$write("\"");
	end
endmodule

/*
	Saída:
	
	a) 50 55 43 2d 4d 2e 47 2e (16)
	b) 32 30 32 34 2d 30 32 (16)
	c) 01000010 01100101 01101100 01101111 00100000 01001000 01101111 01110010 01101001 01111010 01101111 01101110 01110100 01100101 (2)
	d) "noite"
	e) "Manha"
*/