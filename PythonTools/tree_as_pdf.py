import sys, time, uuid
import os
import string
import re
import subprocess
import distutils.version
from optparse import OptionParser
from graphviz import Digraph


class Node:

	def __init__(self, line):
		# parse for values
		vals = line.split()
		# print(vals)
		self.v = int(vals[0])
		self.col = int(vals[1])
		self.typ = vals[2]

		# also get depth
		self.depth = line.count("    ")

		#init
		self.parent = None
		self.kids = []
		self.id = str(uuid.uuid1())


	def addKid(self, kid):
		self.kids.append(kid)


class LogParser:

	def __init__(self, file_name):
		self.file_name = file_name
		self.root = None
		self.f = open(file_name)
		self.groups = {}

		self.parse()



	def parse(self):
		prev_depth = -1
		for l in self.f:
			print(l)
			n = Node(l)
			if (prev_depth == -1): # init line
				prev_depth = n.depth
			
			# If the new node is at the same level as the previous, it is =
			if prev_depth <= n.depth:
				# same depth or went down
				self.tmp_store(n)
			elif n.depth == 0:
				n.kids = self.tmp_rem(1)
				self.root = n
				return
			elif prev_depth > n.depth:
				# went up
				n.kids = self.tmp_rem(prev_depth)
				self.tmp_store(n)
			else:
				print("WTF")

			prev_depth = n.depth

	def tmp_rem(self, depth):
		tmp = self.groups[depth]
		self.groups[depth] = []
		return tmp

	def tmp_store(self, n):
		if n.depth not in self.groups:
			self.groups[n.depth] = []
		self.groups[n.depth].append(n)


def generate(n, dot = None):
	# Recursively populate the dot tree from the root node
	if dot is None:
		dot = Digraph(comment='MiniMax With AlphaBeta Pruning')
		dot.format = 'png'
		dot.node(n.id, str(n.v) + ":" + str(n.col))

	for k in n.kids:
		dot.node(k.id, str(k.v) + ":" + str(k.col))
		dot.edge(n.id, k.id, label=str(k.col))

		generate(k, dot)
	
	if n.parent is None:
		dot.render('test2', view=False)


if __name__ == '__main__':
	file_name = sys.argv[1]
	print( file_name)

	lp = LogParser(file_name)

	generate(lp.root)
