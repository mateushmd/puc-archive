/*
	Guia_0906.v
	842536 - Mateus Henrique Medeiros Diniz
*/

`include "clock.v"

module Guia_0906;
	wire clock;
	
	clock clk ( clock );
	
	wire p;
	
	pulse pulse ( p, clock );
	
	initial begin
		$dumpfile ( "Guia_0906.vcd" );
		$dumpvars ( 1, clock, p );
		#376 $finish;
	end
endmodule

module pulse ( signal, clock );
	input clock;
	output signal;	
	reg signal;
	
	initial 
	begin
		signal = 1'b0;
	end
	
	always @ ( negedge clock )
	begin
		signal = ~signal;
		#5 signal = ~signal;
		#5 signal = ~signal;
	end
endmodule
