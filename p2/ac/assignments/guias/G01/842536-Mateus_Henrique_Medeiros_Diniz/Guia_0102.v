/*
	Guia_0102.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0102;
	reg [5 : 0] binaries [0 : 4]; //Array de 5 posições de 6 bits
	
	initial
	begin: main
		integer i;
	
		binaries[0] = 6'b10101; //6'b => binário de 6 bits
		binaries[1] = 6'b11011;
		binaries[2] = 6'b10010;
		binaries[3] = 6'b101011;
		binaries[4] = 6'b110111;
		
		for(i = 0; i < 5; i++) begin
			$display("%c) %d (10)", "a" + i, binaries[i]);
		end
	end

endmodule

/*
	Saída:
	
	a) 21 (10)
	b) 27 (10)
	c) 18 (10)
	d) 43 (10)
	e) 55 (10)
*/